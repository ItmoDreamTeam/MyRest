//
//  AppDependencies.swift
//  ios
//
//  Created by Артем Розанов on 27.11.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import SwiftKeychainWrapper
import Dip
import shared

typealias UserInfoScene = UserInfoView & ProfileDataDelegate
typealias VerificationCodeScene = VerificationCodeView & PhoneDataDelegate & ContextDataDelegate
typealias SignUpScene = SignUpView & ContextDataDelegate
typealias SignInScene = SignInView & ContextDataDelegate

final class AppDependencies {
  private let container: DependencyContainer

  init(dependencyContainer: DependencyContainer) {
    self.container = dependencyContainer
    configureDependencies()
  }

  private func configureDependencies() {
    // MARK: - KeychainWrapper
    let serviceName = "privateService"
    container.register { KeychainWrapper(serviceName: serviceName) }

    // MARK: - Security
    container.register {
      AccessTokenProviderImpl(keychainWrapper: try self.container.resolve()) as AccessTokenProvider
    }
    guard let accessTokenProvider = try? container.resolve() as AccessTokenProvider else {
      fatalError("AccessTokenProvider not registered")
    }
    AccessTokenProviderCompanion().INSTANCE = accessTokenProvider

    // MARK: - Clients
    container.register { RestaurantClientImpl() as RestaurantClient }
    container.register { UserClientImpl() as UserClient }

    // MARK: - VerificationCodeScene
    container.register { VerificationCodeRouterImpl() as VerificationCodeRouter }
    container.register(.shared) {
      try VerificationCodeInteractorImpl(
        errorHandler: self.container.resolve(),
        userClient: self.container.resolve(),
        accessTokenProvider: accessTokenProvider as! AccessTokenProviderImpl,
        presenter: self.container.resolve()
      ) as VerificationCodeInteractor
    }
    container.register(.shared) {
      VerificationCodePresenterImpl(view: try self.container.resolve()) as VerificationCodePresenter
    }
    container.register(.shared) { VerificationCodeViewController.storyboardInstance()! }
      .resolvingProperties { container, view in
        view.interactor = try container.resolve()
        view.router = try container.resolve()
      }
    .implements(
      VerificationCodeView.self,
      PhoneDataDelegate.self,
      ContextDataDelegate.self,
      VerificationCodeScene.self
    )

    // MARK: - ToSignInRouter
    container.register {
      ToSignInRouterImp(signInScene: try self.container.resolve()) as ToSignInRouter
    }

    // MARK: - ErrorHandler
    container.register { IOSErrorHandler(router: try self.container.resolve()) }

    // MARK: - SignUpScene
    container.register(.shared) {
      try SignUpInteractorImpl(
        userClient: self.container.resolve(),
        errorHandler: self.container.resolve(),
        presenter: self.container.resolve()
        ) as SignUpInteractor
    }
    container.register(.shared) {
      SignUpPresenterImpl(view: try self.container.resolve()) as SignUpPresenter
    }
    container.register(.shared) { SignUpViewController() }
    .resolvingProperties { container, view in
      view.interactor = try container.resolve()
    }
    .implements(SignUpView.self, ContextDataDelegate.self, SignUpScene.self)

    // MARK: - SignInScene
    container.register(.shared) {
      try SignInRouterImpl(
        signUpScene: self.container.resolve(),
        verificationCodeScene: self.container.resolve()
      ) as SignInRouter
    }
    container.register(.shared) {
      try SignInInteractorImpl(
        userClient: self.container.resolve(),
        errorHandler: self.container.resolve(),
        presenter: self.container.resolve()
      ) as SignInInteractor
    }
    container.register(.shared) {
      SignInPresenterImpl(view: try self.container.resolve()) as SignInPresenter
    }
    container.register(.shared) { SignInViewController() }
      .resolvingProperties { container, view in
        view.interactor = try container.resolve()
        view.router = try container.resolve()
      }
    .implements(SignInView.self, ContextDataDelegate.self, SignInScene.self)

    // MARK: - ToSignInRouter
    container.register(.shared) {
      ToSignInRouterImp(signInScene: try self.container.resolve()) as ToSignInRouter
    }

    // MARK: - ErrorHandler
    container.register(.shared) { IOSErrorHandler(router: try self.container.resolve()) }

    // MARK: - UserInfoScene
    container.register { UserInfoViewController.storyboardInstance()! }
      .implements(UserInfoView.self, ProfileDataDelegate.self, UserInfoScene.self)

    // MARK: - UserInfoScene
    container.register {
      UserInfoViewController() as UserInfoView
    }

    // MARK: - RestaurantListScene
    container.register {
      RestaurantListRouterImpl(userInfoScene: try self.container.resolve()) as RestaurantListRouter
    }
    container.register(.shared) {
      try RestaurantListInteractorImpl(
        restaurantClient: self.container.resolve(),
        userClient: self.container.resolve(),
        errorHandler: self.container.resolve(),
        restaurantPresenter: self.container.resolve()
        ) as RestaurantListInteractor
    }
    container.register(.shared) {
      RestaurantListPresenterImpl(view: try self.container.resolve()) as RestaurantListPresenter
    }
    container.register(.shared) {
      RestaurantListViewController()
    }
    .resolvingProperties { container, view in
      view.router = try container.resolve()
      view.interactor = try container.resolve()
    }
    .implements(RestaurantListView.self)
  }

  func getRootViewController() -> RestaurantListView {
    guard let root = try? container.resolve() as RestaurantListView else {
      fatalError("Root ViewController has not be registered")
    }
    return root
  }
}

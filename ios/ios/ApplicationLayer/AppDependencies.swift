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

    // MARK: - SignInScene
    container.register { SignInViewController() as SignInView }

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
        keychainWrapper: self.container.resolve(),
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
    .implements(SignUpView.self)

    // MARK: - RestaurantListScene
    container.register {
      RestaurantListRouterImpl(signUpScene: try self.container.resolve()) as RestaurantListRouter
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

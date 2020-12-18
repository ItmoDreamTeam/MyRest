//
//  AppDependencies.swift
//  ios
//
//  Created by Артем Розанов on 27.11.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import Dip
import shared

final class AppDependencies {
  private let container: DependencyContainer

  init(dependencyContainer: DependencyContainer) {
    self.container = dependencyContainer
    configureDependencies()
  }

  private func configureDependencies() {
    // MARK: - Clients
    container.register { RestaurantClientImpl() as RestaurantClient }

    // MARK: - SignInScene
    container.register { SignInViewController() as SignInView }

    // MARK: - SignUpScene
    container.register { SignUpViewController() as SignUpView }

    // MARK: - RestaurantListScene
    container.register {
      RestaurantListRouterImpl(signUpScene: try self.container.resolve()) as RestaurantListRouter
    }
    container.register(.shared) {
      try RestaurantListInteractorImpl(
        restaurantClient: self.container.resolve(),
        restaurantPresenter: self.container.resolve()) as RestaurantListInteractor
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

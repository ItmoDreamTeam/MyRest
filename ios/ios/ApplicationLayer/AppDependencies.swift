//
//  AppDependencies.swift
//  ios
//
//  Created by Артем Розанов on 27.11.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import Dip

final class AppDependencies {
  private let container: DependencyContainer

  init(dependencyContainer: DependencyContainer) {
    self.container = dependencyContainer
    configureDependencies()
  }

  private func configureDependencies() {
    // MARK: - SignInScene
    container.register { SignInViewController() as SignInView }

    // MARK: - RestaurantListScene
    container.register {
      RestaurantListRouterImpl(signInScene: try self.container.resolve()) as RestaurantListRouter
    }
    container.register { RestaurantListViewController(router: try self.container.resolve()) }
  }

  func getRootViewController() -> RestaurantListViewController {
    guard let root = try? container.resolve() as RestaurantListViewController else {
      fatalError("Root ViewController has not be registered")
    }
    return root
  }
}

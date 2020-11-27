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
    // MARK: - RestaurantListScene
    container.register { RestaurantListViewController() }
  }

  func getRootViewController() -> RestaurantListViewController {
    guard let root = try? container.resolve() as RestaurantListViewController else {
      fatalError("Root ViewController has not be registered")
    }
    return root
  }
}

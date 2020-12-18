//
//  RestaurantListRouter.swift
//  ios
//
//  Created by Артем Розанов on 02.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import SwiftKeychainWrapper

protocol RestaurantListRouter {
  func restaurantListShouldOpenAboutScene(_ restaurantListScene: RestaurantListView)
}

final class RestaurantListRouterImpl: RestaurantListRouter {

  private let signUpScene: SignUpView

  init(signUpScene: SignUpView) {
    self.signUpScene = signUpScene
  }

  func restaurantListShouldOpenAboutScene(_ restaurantListScene: RestaurantListView) {
    guard KeychainWrapper.standard.string(forKey: .token) != nil else {
      openSignUpScene(restaurantListScene)
      return
    }
    openAboutScene(restaurantListScene)
  }

  private func openSignUpScene(_ restaurantListScene: RestaurantListView) {
    restaurantListScene.navigationController?.pushViewController(signUpScene, animated: true)
  }

  private func openAboutScene(_ restaurantListScene: RestaurantListView) {
    fatalError("Not implemented yet")
  }
}

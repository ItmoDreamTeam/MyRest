//
//  RestaurantListRouter.swift
//  ios
//
//  Created by Артем Розанов on 02.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation

protocol RestaurantListRouter {
  func restaurantListShouldOpenScene(_ restaurantListScene: RestaurantListView)
}

final class RestaurantListRouterImpl: RestaurantListRouter {

  private let signInScene: SignInView

  init(signInScene: SignInView) {
    self.signInScene = signInScene
  }

  func restaurantListShouldOpenScene(_ restaurantListScene: RestaurantListView) {
    restaurantListScene.navigationController?.pushViewController(signInScene, animated: true)
  }
}

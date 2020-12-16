//
//  RestaurantListRouter.swift
//  ios
//
//  Created by Артем Розанов on 02.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation

protocol RestaurantListRouter {
  func restaurantListShouldOpenSignInScene(_ restaurantListScene: RestaurantListView)
  func restaurantListShouldOpenRestaurantInfoScene(_ restaurantListScene: RestaurantListView)
}

final class RestaurantListRouterImpl: RestaurantListRouter {

  private let signInScene: SignInView
  private let restaurantInfoScene: RestaurantInfoView

  init(signInScene: SignInView, restaurantInfoScene: RestaurantInfoView) {
    self.signInScene = signInScene
    self.restaurantInfoScene = restaurantInfoScene
  }

  func restaurantListShouldOpenSignInScene(_ restaurantListScene: RestaurantListView) {
    restaurantListScene.navigationController?.pushViewController(signInScene, animated: true)
  }

  func restaurantListShouldOpenRestaurantInfoScene(_ restaurantListScene: RestaurantListView) {
    restaurantListScene.navigationController?.pushViewController(restaurantInfoScene, animated: true)
  }
}

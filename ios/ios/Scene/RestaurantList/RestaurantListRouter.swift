//
//  RestaurantListRouter.swift
//  ios
//
//  Created by Артем Розанов on 02.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import shared

protocol RestaurantListRouter {
  func restaurantListShouldOpenUserInfoScene(_ restaurantListScene: RestaurantListView, pass profile: Profile)
  func restaurantListShouldOpenRestaurantInfoScene(
    _ restaurantListScene: RestaurantListView,
    pass restaurantInfo: RestaurantInfo
  )
}

final class RestaurantListRouterImpl: RestaurantListRouter {

  private let userInfoScene: UserInfoScene
  private let restaurantInfoScene: RestaurantInfoScene

  init(userInfoScene: UserInfoScene, restaurantInfoScene: RestaurantInfoScene) {
    self.userInfoScene = userInfoScene
    self.restaurantInfoScene = restaurantInfoScene
  }

  func restaurantListShouldOpenUserInfoScene(_ restaurantListScene: RestaurantListView, pass profile: Profile) {
    userInfoScene.passedProfile(profile)
    restaurantListScene.navigationController?.pushViewController(userInfoScene, animated: true)
  }

  func restaurantListShouldOpenRestaurantInfoScene(
    _ restaurantListScene: RestaurantListView,
    pass restaurantInfo: RestaurantInfo
  ) {
    restaurantInfoScene.passedRestaurantInfo(restaurantInfo)
    restaurantListScene.navigationController?.pushViewController(restaurantListScene, animated: true)
  }
}

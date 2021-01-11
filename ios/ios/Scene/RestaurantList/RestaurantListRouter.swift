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
}

final class RestaurantListRouterImpl: RestaurantListRouter {

  private let userInfoScene: UserInfoScene

  init(userInfoScene: UserInfoScene) {
    self.userInfoScene = userInfoScene
  }

  func restaurantListShouldOpenUserInfoScene(_ restaurantListScene: RestaurantListView, pass profile: Profile) {
    userInfoScene.passedProfile(profile)
    restaurantListScene.navigationController?.pushViewController(userInfoScene, animated: true)
  }
}

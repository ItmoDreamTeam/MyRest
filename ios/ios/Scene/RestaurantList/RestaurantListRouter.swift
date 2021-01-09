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
  func restaurantListShouldOpenUserInfoScene(_ restaurantListScene: RestaurantListView)
}

final class RestaurantListRouterImpl: RestaurantListRouter {

  private let userInfoScene: UserInfoView

  init(userInfoScene: UserInfoView) {
    self.userInfoScene = userInfoScene
  }

  func restaurantListShouldOpenUserInfoScene(_ restaurantListScene: RestaurantListView) {
    restaurantListScene.navigationController?.pushViewController(userInfoScene, animated: true)
  }
}

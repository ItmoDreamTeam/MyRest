//
//  RestaurantInfoRouter.swift
//  ios
//
//  Created by Артем Розанов on 17.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

protocol RestaurantInfoRouter {
  func restaurantInfoShouldOpenBookScene(_ restaurantInfoView: RestaurantInfoView, passRestaurant restaurant: RestaurantInfo)
}

final class RestaurantInfoRouterImpl: RestaurantInfoRouter {

  private var bookScene: BookScene

  init(bookScene: BookScene) {
    self.bookScene = bookScene
  }

  func restaurantInfoShouldOpenBookScene(_ restaurantInfoView: RestaurantInfoView, passRestaurant restaurant: RestaurantInfo) {
    bookScene.passedRestaurantInfo(restaurant)
    restaurantInfoView.present(bookScene, animated: true, completion: nil)
  }
}

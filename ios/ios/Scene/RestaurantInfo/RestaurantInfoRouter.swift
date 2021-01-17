//
//  RestaurantInfoRouter.swift
//  ios
//
//  Created by Артем Розанов on 17.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit

protocol RestaurantInfoRouter {
  func restaurantInfoShouldOpenBookScene(_ restaurantInfoView: RestaurantInfoView)
}

final class RestaurantInfoRouterImpl: RestaurantInfoRouter {
  func restaurantInfoShouldOpenBookScene(_ restaurantInfoView: RestaurantInfoView) {
    fatalError("Not implemented yet")
  }
}

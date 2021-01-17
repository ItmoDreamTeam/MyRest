//
//  RestarauntIdDataDelegate.swift
//  ios
//
//  Created by Артем Розанов on 11.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import shared

protocol RestaurantInfoDataDelegate: class {
  func passedRestaurantInfo(_ restaurantInfo: RestaurantInfo)
}

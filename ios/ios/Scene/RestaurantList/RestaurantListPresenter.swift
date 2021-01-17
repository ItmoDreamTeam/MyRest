//
//  RestaurantPresenter.swift
//  ios
//
//  Created by Артем Розанов on 07.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit
import shared

protocol RestaurantListPresenter {
  func interactorDidFetched(restaurants: [RestaurantInfo])
  func interactorDidFetched(user: Profile)
}

final class RestaurantListPresenterImpl: RestaurantListPresenter {

  private let view: RestaurantListView

  init(view: RestaurantListView) {
    self.view = view
  }

  func interactorDidFetched(restaurants: [RestaurantInfo]) {
    view.onRestaurantsFetchCompleted(restaurants)
  }

  func interactorDidFetched(user: Profile) {
    view.onUserFetchCompleted(user)
  }
}

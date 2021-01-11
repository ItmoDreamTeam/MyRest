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
  func interactorDidFetched(restaurants: Result<[RestaurantInfo], Error>)
  func interactorDidFetched(user: Profile)
}

final class RestaurantListPresenterImpl: RestaurantListPresenter {

  private let view: RestaurantListView

  init(view: RestaurantListView) {
    self.view = view
  }

  func interactorDidFetched(restaurants: Result<[RestaurantInfo], Error>) {
    switch restaurants {
    case .success(let restaurantsInfos):
      let viewModel = restaurantsInfos.map {
        return RestaurantViewModel(
          id: $0.id, 
          name: $0.name,
          rating: $0.internalRating,
          avatar: UIImage(named: "restaurantPlaceholder") ?? UIImage()
        )
      }
      view.onRestaurantsFetchCompleted(viewModel)
    case .failure(let error):
      view.onRestaurantsFetchError(error)
    }
  }

  func interactorDidFetched(user: Profile) {
    view.onUserFetchCompleted(user)
  }
}

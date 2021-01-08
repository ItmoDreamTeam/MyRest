//
//  RestaurantPresenter.swift
//  ios
//
//  Created by Артем Розанов on 06.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import shared

protocol RestaurantListInteractor {
  func restaurantListDidRequestRestaurants(_ restaurantListView: RestaurantListView, byKeyword: String)
}

final class RestaurantListInteractorImpl: RestaurantListInteractor {

  private let restaurantClient: RestaurantClient
  private let userClient: UserClient
  private let errorHandler: IOSErrorHandler
  private let restaurantPresenter: RestaurantListPresenter

  private var pageSize = 10
  private var isFetching = false
  private var currentPage = 0

  init(
    restaurantClient: RestaurantClient,
    userClient: UserClient,
    errorHandler: IOSErrorHandler,
    restaurantPresenter: RestaurantListPresenter
  ) {
    self.restaurantClient = restaurantClient
    self.userClient = userClient
    self.errorHandler = errorHandler
    self.restaurantPresenter = restaurantPresenter
  }

  func restaurantListDidRequestRestaurants(_ restaurantListView: RestaurantListView, byKeyword: String) {
    guard !isFetching else { return }
    isFetching = true

    let pageable = Pageable(pageNumber: Int32(currentPage), pageSize: Int32(pageSize))
    restaurantClient.search(keyword: byKeyword, pageable: pageable) { [weak self] restaurantsPage, error in
      guard let self = self else { return }
      guard let restaurantsPage = restaurantsPage, error == nil else {
        // swiftlint:disable force_unwrapping
        self.restaurantPresenter.interactorDidFetched(restaurants: .failure(error!))
        return
      }
      guard let restaurants = restaurantsPage.content as? [RestaurantInfo] else {
        self.restaurantPresenter.interactorDidFetched(
          restaurants: .failure(RestaurantListinteractorError.unexpectedContent)
        )
        return
      }
      self.currentPage += 1
      self.isFetching = false
      self.restaurantPresenter.interactorDidFetched(restaurants: .success(restaurants))
    }
  }
}

extension RestaurantListInteractorImpl {
  private enum RestaurantListinteractorError: Error {
    case unexpectedContent
  }
}

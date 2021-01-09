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
  func restaurantListDidRequestUserInfo(_ restaurantListView: RestaurantListView)
}

final class RestaurantListInteractorImpl: RestaurantListInteractor {

  private let restaurantClient: RestaurantClient
  private let userClient: UserClient
  private let errorHandler: IOSErrorHandler
  private let presenter: RestaurantListPresenter

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
    self.presenter = restaurantPresenter
  }

  func restaurantListDidRequestRestaurants(_ restaurantListView: RestaurantListView, byKeyword: String) {
    guard !isFetching else { return }
    isFetching = true

    let pageable = Pageable(pageNumber: Int32(currentPage), pageSize: Int32(pageSize))
    restaurantClient.search(keyword: byKeyword, pageable: pageable) { [weak self] restaurantsPage, error in
      guard let self = self else { return }
      guard let restaurantsPage = restaurantsPage, error == nil else {
        // swiftlint:disable force_unwrapping
        self.presenter.interactorDidFetched(restaurants: .failure(error!))
        return
      }
      guard let restaurants = restaurantsPage.content as? [RestaurantInfo] else {
        self.presenter.interactorDidFetched(
          restaurants: .failure(RestaurantListinteractorError.unexpectedContent)
        )
        return
      }
      self.currentPage += 1
      self.isFetching = false
      self.presenter.interactorDidFetched(restaurants: .success(restaurants))
    }
  }

  func restaurantListDidRequestUserInfo(_ restaurantListView: RestaurantListView) {
    userClient.getMe { [weak self] user, error in
      guard let user = user else {
        self?.errorHandler.handleNSError(context: restaurantListView, error: error)
        return
      }
      self?.presenter.interactorDidFetched(user: user)
    }
  }
}

extension RestaurantListInteractorImpl {
  private enum RestaurantListinteractorError: Error {
    case unexpectedContent
  }
}

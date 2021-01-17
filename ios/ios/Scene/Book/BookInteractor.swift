//
//  BookInteractor.swift
//  ios
//
//  Created by Артем Розанов on 17.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

protocol BookInteractor {
  func bookDidRequestTables(_ bookView: BookView, forRestautandId restaurantId: Int64)
}

final class BookInteractorImpl: BookInteractor {

  private let tableClient: TableClient
  private let reservationClient: ReservationClient
  private let errorHandler: IOSErrorHandler
  private let presenter: BookPresenter

  init(
    tableClient: TableClient,
    reservationClient: ReservationClient,
    errorHandler: IOSErrorHandler,
    presenter: BookPresenter
  ) {
    self.tableClient = tableClient
    self.reservationClient = reservationClient
    self.errorHandler = errorHandler
    self.presenter = presenter
  }

  func bookDidRequestTables(_ bookView: BookView, forRestautandId restaurantId: Int64) {
    tableClient.getRestaurantTables(restaurantId: restaurantId) { [weak self] tables, error in
      guard let tables = tables, error == nil else {
        self?.errorHandler.handleNSError(context: bookView, error: error)
        return
      }
      self?.presenter.interactorDidFetched(tables: tables)
    }
  }

  func bookShouldBookTable(_ bookView: BookView, withId tableId: Int64, onDate date: Date) {
    guard let activeUntil = Calendar.current.date(byAdding: .hour, value: 3, to: date) else { return }
    reservationClient.submitReservationForApproval(
      tableId: tableId,
      activeFrom: date.toKotlindatetimeLocalDateTime(),
      activeUntil: activeUntil.toKotlindatetimeLocalDateTime()
    ) { [weak self] reservation, error in
      guard let reservation = reservation, error == nil else {
        self?.errorHandler.handleNSError(context: bookView, error: error)
        return
      }
      self?.presenter.interactorBookedTable(reservation: reservation)
    }
  }
}

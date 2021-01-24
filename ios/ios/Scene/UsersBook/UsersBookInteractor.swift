//
//  UserBookInteractor.swift
//  ios
//
//  Created by Артем Розанов on 24.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

protocol UsersBookInteractor {
  func usersBookDidRequestReservation(_ usersBookView: UsersBookView)
}

final class UsersBookInteractorImpl: UsersBookInteractor {

  private let reservationClient: ReservationClient
  private let errorHandler: IOSErrorHandler
  private let presenter: UsersBookPresenter

  init(
    reservationClient: ReservationClient,
    errorHandler: IOSErrorHandler,
    presenter: UsersBookPresenter
    ) {
    self.reservationClient = reservationClient
    self.errorHandler = errorHandler
    self.presenter = presenter
  }

  func usersBookDidRequestReservation(_ usersBookView: UsersBookView) {
    let date = Date().toKotlinxDatetimeLocalDate()
    reservationClient.getReservationsOfUser(date: date) { [weak self] reservations, error in
      guard let reservations = reservations, error != nil else {
        self?.errorHandler.handleNSError(context: usersBookView, error: error)
        return
      }
      self?.presenter.interactorDidFetched(reservations: reservations)
    }
  }
}

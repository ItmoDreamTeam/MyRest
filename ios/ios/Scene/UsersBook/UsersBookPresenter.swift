//
//  UsersBookInteractor.swift
//  ios
//
//  Created by Артем Розанов on 24.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import shared

protocol UsersBookPresenter {
  func interactorDidFetched(reservations: [ReservationInfo])
}

final class UsersBookPresenterImpl: UsersBookPresenter {

  private let view: UsersBookView

  init(view: UsersBookView) {
    self.view = view
  }

  func interactorDidFetched(reservations: [ReservationInfo]) {
    view.onReservationsFetchCompleted(reservations)
  }
}

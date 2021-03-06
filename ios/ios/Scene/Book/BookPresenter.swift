//
//  BookPresenter.swift
//  ios
//
//  Created by Артем Розанов on 17.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

protocol BookPresenter {
  func interactorDidFetched(tables: [TableView])
  func interactorBookedTable(reservation: ReservationInfo)
}

final class BookPresenterImpl: BookPresenter {

  private let view: BookView

  init(view: BookView) {
    self.view = view
  }

  func interactorDidFetched(tables: [TableView]) {
    let sortedTables = tables.sorted { $0.info.number > $1.info.number }
    view.onTablesFetchCompleted(sortedTables)
  }

  func interactorBookedTable(reservation: ReservationInfo) {
    view.presentToast(
      message: "Ваше бронирование зарегистрированно и появится на странице бронирований",
      onTime: .now() + .seconds(2)
    )
    view.onReservationCompleted()
  }
}

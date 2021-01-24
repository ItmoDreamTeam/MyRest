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

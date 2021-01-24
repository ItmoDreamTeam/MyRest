//
//  Kotlinx_datetimeLocalDateTime+Utils.swift
//  ios
//
//  Created by Артем Розанов on 24.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import shared

extension Kotlinx_datetimeLocalDateTime {
  func toString() -> String {
    "\(dayOfMonth)/\(month)/\(year) \(hour):\(minute)"
  }
}

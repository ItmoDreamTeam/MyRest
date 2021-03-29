//
//  Date+Utils.swift
//  ios
//
//  Created by Артем Розанов on 17.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

extension Date {
  func get(_ components: Calendar.Component..., calendar: Calendar = Calendar.current) -> DateComponents {
    return calendar.dateComponents(Set(components), from: self)
  }

  func get(_ component: Calendar.Component, calendar: Calendar = Calendar.current) -> Int {
    return calendar.component(component, from: self)
  }

  func toKotlindatetimeLocalDateTime() -> Kotlinx_datetimeLocalDateTime {
    Kotlinx_datetimeLocalDateTime(
      year: Int32(self.get(.year)),
      monthNumber: Int32(self.get(.month)),
      dayOfMonth: Int32(self.get(.day)),
      hour: Int32(self.get(.hour)),
      minute: Int32(self.get(.minute)),
      second: Int32(self.get(.second)),
      nanosecond: Int32(self.get(.nanosecond))
    )
  }
}

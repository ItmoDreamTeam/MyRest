//
//  Date+Utils.swift
//  ios
//
//  Created by Артем Розанов on 17.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation

extension Date {
    func get(_ components: Calendar.Component..., calendar: Calendar = Calendar.current) -> DateComponents {
        return calendar.dateComponents(Set(components), from: self)
    }

    func get(_ component: Calendar.Component, calendar: Calendar = Calendar.current) -> Int {
        return calendar.component(component, from: self)
    }
}

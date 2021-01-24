//
//  ReservationInfoCell.swift
//  ios
//
//  Created by Артем Розанов on 20.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

final class ReservationInfoCell: UITableViewCell, ConfigurableView {
  typealias Model = ReservationInfo

  @IBOutlet weak var dateLabel: UILabel!
  @IBOutlet weak var restaurantNameLabel: UILabel!
  @IBOutlet weak var statusLabel: UILabel!
  @IBOutlet weak var statusImageView: UIImageView!

  func configure(with model: ReservationInfo) {
    fatalError("Not implemented yet")
  }
}

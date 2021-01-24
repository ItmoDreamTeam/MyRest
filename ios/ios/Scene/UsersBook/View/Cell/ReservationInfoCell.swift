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
  static let reuseId = "ReservationInfoCell"
  typealias Model = ReservationInfo

  @IBOutlet weak var dateLabel: UILabel!
  @IBOutlet weak var restaurantNameLabel: UILabel!
  @IBOutlet weak var statusLabel: UILabel!
  @IBOutlet weak var statusImageView: UIImageView!

  func configure(with model: ReservationInfo) {
    dateLabel.text = model.activeFrom.toString()
    restaurantNameLabel.text = "\(model.table.restaurant)"
    checkStatus(status: model.status)
  }

  private func checkStatus(status: ReservationStatus) {
    switch status {
    case .approved:
      statusImageView.image = UIImage(named: "approved")
      statusLabel.text = "Подтверждено"
    case .completed:
      statusLabel.text = "Завершено"
    case .inProgress:
      statusLabel.text = "В процессе"
    case .pending:
      statusImageView.image = UIImage(named: "pending")
      statusLabel.text = "В ожидание"
    case .rejected:
      statusImageView.image = UIImage(named: "rejected")
      statusLabel.text = "Отменено"
    default:
      statusLabel.text = ""
    }
  }
}

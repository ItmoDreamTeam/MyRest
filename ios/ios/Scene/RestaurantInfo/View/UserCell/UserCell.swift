//
//  UserCell.swift
//  ios
//
//  Created by Артем Розанов on 15.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit

final class UserCell: UITableViewCell, ConfigurableView {
  typealias Model = UserReviewViewModel

  @IBOutlet weak var userImageView: UIImageView!
  @IBOutlet weak var userName: UILabel!
  @IBOutlet weak var dateLabel: UILabel!
  @IBOutlet weak var reviewLabel: UILabel!
  
  func configure(with model: Model) {
    userName.text = model.name
    userImageView.image = model.avatar
    dateLabel.text = model.date
    reviewLabel.text = model.review
  }
}

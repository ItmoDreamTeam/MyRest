//
//  RestaurantCell.swift
//  ios
//
//  Created by Артем Розанов on 27.11.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit
import shared
//import SDWebImage

final class RestaurantCell: UICollectionViewCell, ConfigurableView {
  static let reuseId = "RestaurantCell"

  typealias Model = RestaurantInfo
  
  @IBOutlet weak var restaurantNameLabel: UILabel!
  @IBOutlet weak var cusineLabel: UILabel!
  @IBOutlet weak var ratingLabel: UILabel!
  @IBOutlet weak var restaurantImageView: UIImageView!

  override func layoutSubviews() {
    restaurantImageView.contentMode = .scaleAspectFit
  }

  func configure(with model: RestaurantInfo) {
    restaurantNameLabel.text = model.name
    ratingLabel.text = "\(model.internalRating)"
    guard
      let avatar = model.avatar,
      let url = URL(string: avatar.url())
    else { return }
    restaurantImageView.sd_setImage(with: url, placeholderImage: UIImage(named: "restaurantPlaceholder"))
  }
}

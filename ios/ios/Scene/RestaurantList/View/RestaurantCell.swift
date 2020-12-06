//
//  RestaurantCell.swift
//  ios
//
//  Created by Артем Розанов on 27.11.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit

final class RestaurantCell: UICollectionViewCell, ConfigurableView {
  static let reuseId = "RestaurantCell"

  typealias Model = RestaurantViewModel
  
  @IBOutlet weak var restaurantNameLabel: UILabel!
  @IBOutlet weak var cusineLabel: UILabel!
  @IBOutlet weak var ratingLabel: UILabel!
  @IBOutlet weak var restaurantImageView: UIImageView!
  
  override func awakeFromNib() {
    super.awakeFromNib()
    // Initialization code
  }

  func configure(with model: RestaurantViewModel) {
    restaurantNameLabel.text = model.name
    ratingLabel.text = "\(model.rating)"
    restaurantImageView.image = model.avatar
  }
}

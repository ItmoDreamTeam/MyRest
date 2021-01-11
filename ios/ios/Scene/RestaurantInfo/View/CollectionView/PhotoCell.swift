//
//  PhotoCell.swift
//  ios
//
//  Created by Артем Розанов on 14.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit
import shared

final class PhotoCell: UICollectionViewCell, ConfigurableView {
  static let reuseId = "PhotoCell"
  typealias Model = RestaurantInfo

  private lazy var imageView: UIImageView = {
    let image = UIImageView()
    image.translatesAutoresizingMaskIntoConstraints = false
    image.contentMode = .scaleAspectFill
    return image
  }()

  override init(frame: CGRect) {
    super.init(frame: frame)
    translatesAutoresizingMaskIntoConstraints = false
    clipsToBounds = true
    addSubview(imageView)
    imageView.frame = bounds
  }

  required init?(coder: NSCoder) {
    return nil
  }

  func configure(with model: RestaurantInfo) {
    fatalError("Not implemented yet")
  }
}

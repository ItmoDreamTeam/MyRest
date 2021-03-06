//
//  PhotoCell.swift
//  ios
//
//  Created by Артем Розанов on 14.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit
import shared
import SDWebImage

final class PhotoCell: UICollectionViewCell, ConfigurableView {
  static let reuseId = "PhotoCell"
  typealias Model = AttachmentMetadata

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

  override func layoutSubviews() {
    super.layoutSubviews()
    layer.cornerRadius = Constant.buttonCornerRadius
  }

  func configure(with model: AttachmentMetadata) {
    guard let url = URL(string: model.url()) else { return }
    imageView.sd_setImage(with: url, placeholderImage: UIImage(named: "placeholder"))
  }
}

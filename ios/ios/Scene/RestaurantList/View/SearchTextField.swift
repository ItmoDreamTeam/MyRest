//
//  SearchTextField.swift
//  ios
//
//  Created by Артем Розанов on 27.11.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit

final class SearchTextField: UITextField {
  private lazy var imageView: UIImageView = {
    let imageView = UIImageView(frame: CGRect(x: 0, y: 0, width: 40, height: 40))
    imageView.image = UIImage(named: "search")
    return imageView
  }()

  override init(frame: CGRect) {
    super.init(frame: frame)

    backgroundColor = UIColor(rgb: 0xF5F5F5)
    addSubview(imageView)
    imageView.centerYAnchor.constraint(equalTo: centerYAnchor).isActive = true
    imageView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 10).isActive = true
  }

  required init?(coder: NSCoder) {
    return nil
  }
}

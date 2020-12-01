//
//  SearchView.swift
//  ios
//
//  Created by Артем Розанов on 02.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit

final class SearchView: UIView {

  private lazy var imageView: UIImageView = {
    let imageView = UIImageView(image: UIImage(named: "search"))
    imageView.translatesAutoresizingMaskIntoConstraints = false
    return imageView
  }()

  private lazy var textField: UITextField = {
    let textField = UITextField()
    textField.translatesAutoresizingMaskIntoConstraints = false
    textField.placeholder = "Найдите ресторан"
    return textField
  }()

  init() {
    super.init(frame: .zero)
    translatesAutoresizingMaskIntoConstraints = false
    backgroundColor = UIColor.lightGray
    heightAnchor.constraint(equalToConstant: 30).isActive = true
    layer.cornerRadius = 15
    addSubview(imageView)
    imageView.heightAnchor.constraint(equalToConstant: 15).isActive = true
    imageView.widthAnchor.constraint(equalToConstant: 15).isActive = true
    imageView.centerYAnchor.constraint(equalTo: centerYAnchor).isActive = true
    imageView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 10).isActive = true
    addSubview(textField)
    textField.leadingAnchor.constraint(equalTo: imageView.trailingAnchor, constant: 5).isActive = true
    textField.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -10).isActive = true
    textField.topAnchor.constraint(equalTo: topAnchor).isActive = true
    textField.bottomAnchor.constraint(equalTo: bottomAnchor).isActive = true
  }

  required init?(coder: NSCoder) {
    return nil
  }
}

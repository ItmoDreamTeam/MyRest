//
//  RestaurantInfoView.swift
//  ios
//
//  Created by Артем Розанов on 14.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit

final class InfoView: UIView {

  private lazy var stackView: UIStackView = {
    let stackView = UIStackView()
    stackView.axis = .vertical
    stackView.translatesAutoresizingMaskIntoConstraints = false
    stackView.alignment = .fill
    stackView.spacing = 5
    return stackView
  }()

  private lazy var nameLabel: UILabel = {
    return getLabel()
  }()

  private lazy var websiteLabel: UILabel = {
    return getLabel()
  }()

  private lazy var phoneLabel: UILabel = {
    return getLabel()
  }()

  private lazy var emailLabel: UILabel = {
    return getLabel()
  }()

  private lazy var ratingLabel: UILabel = {
    return getLabel()
  }()

  init(viewModel: RestaurantViewModel) {
    super.init(frame: .zero)
    addSubview(stackView)
    stackView.topAnchor.constraint(equalTo: topAnchor).isActive = true
    stackView.leadingAnchor.constraint(equalTo: leadingAnchor).isActive = true
    stackView.trailingAnchor.constraint(equalTo: trailingAnchor).isActive = true
    stackView.bottomAnchor.constraint(equalTo: bottomAnchor).isActive = true

    nameLabel.text = "\(viewModel.name)"
    stackView.addArrangedSubview(nameLabel)
    if let website = viewModel.websiteUrl {
      websiteLabel.text = website
      stackView.addArrangedSubview(websiteLabel)
    }
    if let phone = viewModel.phone {
      phoneLabel.text = phone
      stackView.addArrangedSubview(phoneLabel)
    }
    if let email = viewModel.email {
      emailLabel.text = email
      stackView.addArrangedSubview(emailLabel)
    }
    ratingLabel.text = "\(viewModel.rating)"
    stackView.addArrangedSubview(ratingLabel)
  }

  required init?(coder: NSCoder) {
    return nil
  }

  private func getLabel() -> UILabel {
    let label = UILabel()
    label.translatesAutoresizingMaskIntoConstraints = false
    return label
  }
}

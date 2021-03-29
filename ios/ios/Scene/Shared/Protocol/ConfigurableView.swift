//
//  ConfigurableView.swift
//  ios
//
//  Created by Артем Розанов on 27.11.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

protocol ConfigurableView {
  associatedtype Model
  func configure(with model: Model)
}

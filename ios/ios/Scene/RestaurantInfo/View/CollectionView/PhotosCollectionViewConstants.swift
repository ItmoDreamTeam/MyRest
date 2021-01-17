//
//  PhotosCollectionViewConstants.swift
//  ios
//
//  Created by Артем Розанов on 17.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit

enum PhotosCollectionViewConstants {
  static let minimumLineSpacing: CGFloat = 3
  static let leftDistanceToView: CGFloat = 40
  static let rightDistanceToView: CGFloat = 40
  static let sectionInsets = UIEdgeInsets(
    top: 0,
    left: leftDistanceToView,
    bottom: 0,
    right: rightDistanceToView
  )
  static let cellWidth: CGFloat = (UIScreen.main.bounds.width
    - rightDistanceToView
    - leftDistanceToView
    - (minimumLineSpacing / 2)) / 1.5
}

//
//  LoadingFotter.swift
//  ios
//
//  Created by Артем Розанов on 27.11.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit

final class LoadingFotter: UICollectionReusableView {
  static let reuseId = "LoadingFotter"

  lazy var activityIndicatorView: UIActivityIndicatorView = {
    let frame = CGRect(x: 0, y: 0, width: self.frame.size.width, height: 100)
    let indicator = UIActivityIndicatorView(frame: frame)
    indicator.startAnimating()
    indicator.hidesWhenStopped = true
    return indicator
  }()

  override func layoutSubviews() {
    super.layoutSubviews()
    addSubview(activityIndicatorView)
  }
}

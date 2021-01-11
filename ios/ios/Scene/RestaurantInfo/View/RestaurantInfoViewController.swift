//
//  RestaurantInfoViewController.swift
//  ios
//
//  Created by Артем Розанов on 14.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit
import shared

protocol RestaurantInfoView: UIViewController {}

final class RestaurantInfoViewController: UIViewController, RestaurantInfoView {
  static func storyboardInstance() -> Self? {
    let storyboard = UIStoryboard(name: String(describing: self), bundle: nil)
    return storyboard.instantiateInitialViewController() as? Self
  }

  private var restaurant: RestaurantInfo?

  @IBOutlet weak var photosCollectionView: PhotosCollectionView!
  @IBOutlet weak var descriptionLabel: UILabel!
  @IBOutlet weak var websiteLabel: UILabel!
  @IBOutlet weak var emailLabel: UILabel!
  @IBOutlet weak var ratingLabel: UILabel!
  

  override func viewDidLoad() {
    super.viewDidLoad()
    view.backgroundColor = .white
    configureNavBar()
  }

  private func configureNavBar() {
    navigationItem.title = restaurant?.name
    navigationItem.largeTitleDisplayMode = .always
    navigationController?.navigationBar.prefersLargeTitles = true
  }
}

extension RestaurantInfoViewController: RestaurantInfoDataDelegate {
  func passedRestaurantInfo(_ restaurantInfo: RestaurantInfo) {
    self.restaurant = restaurantInfo
  }
}

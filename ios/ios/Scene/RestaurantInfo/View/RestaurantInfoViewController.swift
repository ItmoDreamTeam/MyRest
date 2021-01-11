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
    configureLabels()
  }

  private func configureNavBar() {
    navigationItem.title = restaurant?.name
    navigationItem.largeTitleDisplayMode = .always
    navigationController?.navigationBar.prefersLargeTitles = true
  }

  private func configureLabels() {
    guard let restaurant = restaurant else { return }
    descriptionLabel.text = restaurant.description_
    setLabelText(label: websiteLabel, text: restaurant.websiteUrl)
    setLabelText(label: emailLabel, text: restaurant.email)
    setLabelText(label: ratingLabel, text: "\(restaurant.externalRating)")
  }

  private func setLabelText(label: UILabel, text: String?) {
    if let text = text {
      label.text = "\(label.text ?? "") \(text)"
    } else {
      label.isEnabled = false
    }
  }
}

extension RestaurantInfoViewController: RestaurantInfoDataDelegate {
  func passedRestaurantInfo(_ restaurantInfo: RestaurantInfo) {
    self.restaurant = restaurantInfo
  }
}

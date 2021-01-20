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

  var router: RestaurantInfoRouter?

  private var restaurant: RestaurantInfo?

  @IBOutlet private weak var photosCollectionView: PhotosCollectionView!
  @IBOutlet private weak var websiteLabel: UILabel!
  @IBOutlet private weak var descriptionTextView: UITextView!
  @IBOutlet private weak var emailLabel: UILabel!
  @IBOutlet private weak var ratingLabel: UILabel!
  @IBOutlet private weak var registerButton: UIButton!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    view.backgroundColor = .white
  }

  override func viewWillAppear(_ animated: Bool) {
    super.viewWillAppear(animated)
    hideLabels()
    configureNavBar()
    showLabels()
    configureCollectionView()
    configureButton()
  }

  override func viewDidDisappear(_ animated: Bool) {
    super.viewDidDisappear(animated)
    hideLabels()
  }

  private func configureNavBar() {
    navigationItem.title = restaurant?.name
    navigationItem.largeTitleDisplayMode = .always
    navigationController?.navigationBar.prefersLargeTitles = true
  }

  private func configureButton() {
    registerButton.layer.cornerRadius = Constant.buttonCornerRadius
    registerButton.backgroundColor = .lightGray
  }

  private func showLabels() {
    guard let restaurant = restaurant else { return }
    descriptionTextView.text = restaurant.description_
    setLabelText(label: websiteLabel, prefixText: "Сайт: ", text: restaurant.websiteUrl)
    setLabelText(label: emailLabel, prefixText: "Почта: ", text: restaurant.email)
    setLabelText(label: ratingLabel, prefixText: "Рейтинг: ", text: "\(restaurant.externalRating)")
  }

  private func hideLabels() {
    hideLabel(label: websiteLabel)
    hideLabel(label: emailLabel)
    hideLabel(label: ratingLabel)
  }

  private func hideLabel(label: UILabel) {
    label.text = ""
    label.isEnabled = false
  }

  private func setLabelText(label: UILabel, prefixText: String, text: String?) {
    if let text = text {
      label.isEnabled = true
      label.text = prefixText + text
    }
  }

  private func configureCollectionView() {
    guard let restaurant = restaurant else { return }
    photosCollectionView.configure(with: restaurant)
    DispatchQueue.main.async {
      self.photosCollectionView.reloadData()
    }
  }

  @IBAction func registerTapped(_ sender: UIButton) {
    guard let restaurant = restaurant else { return }
    router?.restaurantInfoShouldOpenBookScene(self, passRestaurant: restaurant)
  }
}

extension RestaurantInfoViewController: RestaurantInfoDataDelegate {
  func passedRestaurantInfo(_ restaurantInfo: RestaurantInfo) {
    self.restaurant = restaurantInfo
  }
}

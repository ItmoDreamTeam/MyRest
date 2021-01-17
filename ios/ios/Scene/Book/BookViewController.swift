//
//  BookViewController.swift
//  ios
//
//  Created by Артем Розанов on 17.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

protocol BookView: UIViewController {}

final class BookViewController: UIViewController, BookView {
  static func storyboardInstance() -> Self? {
    let storyboard = UIStoryboard(name: String(describing: self), bundle: nil)
    return storyboard.instantiateInitialViewController() as? Self
  }

  private var restaurant: RestaurantInfo?

  @IBOutlet weak var tablePicker: UIPickerView!
  @IBOutlet weak var datePicker: UIDatePicker!

  override func viewDidLoad() {
    super.viewDidLoad()
  }
}

extension BookViewController: RestaurantInfoDataDelegate {
  func passedRestaurantInfo(_ restaurantInfo: RestaurantInfo) {
    restaurant = restaurantInfo
  }
}

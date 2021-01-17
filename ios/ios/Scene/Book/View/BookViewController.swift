//
//  BookViewController.swift
//  ios
//
//  Created by Артем Розанов on 17.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

protocol BookView: UIViewController {
  func onTablesFetchCompleted(_ tables: [TableView])
}

final class BookViewController: UIViewController, BookView {
  static func storyboardInstance() -> Self? {
    let storyboard = UIStoryboard(name: String(describing: self), bundle: nil)
    return storyboard.instantiateInitialViewController() as? Self
  }

  var interactor: BookInteractor?

  private var restaurant: RestaurantInfo?

  @IBOutlet weak var tablePicker: UIPickerView!
  @IBOutlet weak var datePicker: UIDatePicker!

  override func viewDidLoad() {
    super.viewDidLoad()
    guard let restaurantId = restaurant?.id else { return }
    interactor?.bookDidRequestTables(self, forRestautandId: restaurantId)
  }

  func onTablesFetchCompleted(_ tables: [TableView]) {
    fatalError("Not implemented yet")
  }
}

extension BookViewController: RestaurantInfoDataDelegate {
  func passedRestaurantInfo(_ restaurantInfo: RestaurantInfo) {
    restaurant = restaurantInfo
  }
}

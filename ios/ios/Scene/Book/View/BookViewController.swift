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
  private var tables: [TableView] = []

  @IBOutlet weak var tablePicker: UIPickerView!
  @IBOutlet weak var datePicker: UIDatePicker!

  override func viewDidLoad() {
    super.viewDidLoad()
    guard let restaurantId = restaurant?.id else { return }
    configurePickers()
    interactor?.bookDidRequestTables(self, forRestautandId: restaurantId)
  }

  private func configurePickers() {
    tablePicker.dataSource = self
    tablePicker.delegate = self
    configureDatePicker()
  }

  private func configureDatePicker() {
    let calendar = Calendar(identifier: .gregorian)
    var component = DateComponents()
    component.day = 7
    let maxDate = calendar.date(byAdding: component, to: Date())
    datePicker.minimumDate = Date()
    datePicker.maximumDate = maxDate
  }

  func onTablesFetchCompleted(_ tables: [TableView]) {
    self.tables = tables
    DispatchQueue.main.async {
      self.tablePicker.reloadAllComponents()
    }
  }
}

extension BookViewController: UIPickerViewDataSource {
  func numberOfComponents(in pickerView: UIPickerView) -> Int {
    1
  }

  func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
    tables.count
  }
}

extension BookViewController: UIPickerViewDelegate {
  func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
    "Столик \(tables[row].info.number), мест - \(tables[row].info.numberOfSeats)"
  }
}

extension BookViewController: RestaurantInfoDataDelegate {
  func passedRestaurantInfo(_ restaurantInfo: RestaurantInfo) {
    restaurant = restaurantInfo
  }
}

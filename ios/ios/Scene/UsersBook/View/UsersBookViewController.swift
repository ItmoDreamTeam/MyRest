//
//  UsersBookViewController.swift
//  ios
//
//  Created by Артем Розанов on 20.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import shared

protocol UsersBookView: UIViewController {
  func onReservationsFetchCompleted(_ reservations: [ReservationInfo])
}

final class UsersBookViewController: UIViewController, UsersBookView {
  static func storyboardInstance() -> Self? {
    let storyboard = UIStoryboard(name: String(describing: self), bundle: nil)
    return storyboard.instantiateInitialViewController() as? Self
  }

  var interactor: UsersBookInteractor?

  private var reservations: [ReservationInfo] = []

  @IBOutlet private weak var tableView: UITableView!

  override func viewDidLoad() {
    super.viewDidLoad()
    configureTableView()
  }

  override func viewWillAppear(_ animated: Bool) {
    super.viewWillAppear(true)
    configureNavBar()
    interactor?.usersBookDidRequestReservation(self)
  }

  private func configureNavBar() {
    navigationItem.largeTitleDisplayMode = .never
    navigationController?.navigationBar.prefersLargeTitles = true
    navigationItem.title = "Бронирования"
  }

  private func configureTableView() {
    tableView.dataSource = self
    tableView.register(
      UINib(nibName: ReservationInfoCell.reuseId, bundle: nil), forCellReuseIdentifier: ReservationInfoCell.reuseId
    )
  }

  func onReservationsFetchCompleted(_ reservations: [ReservationInfo]) {
    self.reservations = reservations
    DispatchQueue.main.async {
      self.tableView.reloadData()
    }
  }
}

extension UsersBookViewController: UITableViewDataSource {
  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    reservations.count
  }

  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    guard let cell = tableView.dequeueReusableCell(
      withIdentifier: ReservationInfoCell.reuseId,
      for: indexPath
      ) as? ReservationInfoCell
    else { return UITableViewCell() }
    cell.configure(with: reservations[indexPath.row])
    return cell
  }
}

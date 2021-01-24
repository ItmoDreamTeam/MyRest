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

  private var reservations: [ReservationInfo] = []

  @IBOutlet private weak var tableView: UITableView!

  override func viewDidLoad() {
    super.viewDidLoad()
    configureTableView()
  }

  private func configureTableView() {
    tableView.dataSource = self
    tableView.register(
      UINib(nibName: ReservationInfoCell.reuseId, bundle: nil), forCellReuseIdentifier: ReservationInfoCell.reuseId
    )
  }

  func onReservationsFetchCompleted(_ reservations: [ReservationInfo]) {
    fatalError("Not implemented yet")
  }
}

extension UsersBookViewController: UITableViewDataSource {
  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    reservations.count
  }

  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    guard
      let cell = tableView.dequeueReusableCell(withIdentifier: ReservationInfoCell.reuseId, for: indexPath) as? ReservationInfoCell
    else { return UITableViewCell() }
    cell.configure(with: reservations[indexPath.row])
    return cell
  }
}

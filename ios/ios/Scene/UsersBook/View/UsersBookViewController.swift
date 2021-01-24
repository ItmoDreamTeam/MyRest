//
//  UsersBookViewController.swift
//  ios
//
//  Created by Артем Розанов on 20.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit

protocol UsersBookView: UIViewController {}

final class UsersBookViewController: UIViewController, UsersBookView {

  @IBOutlet weak var tableView: UITableView!
  

  override func viewDidLoad() {
    super.viewDidLoad()
  }
}

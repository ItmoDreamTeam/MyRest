//
//  UserInfoViewController.swift
//  ios
//
//  Created by Артем Розанов on 28.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit

protocol UserInfoView: UIViewController {}

final class UserInfoViewController: UIViewController, UserInfoView {
  static let storyboardName = "UserInfoViewController"
  static func storyboardInstance() -> Self? {
    let storyboard = UIStoryboard(name: storyboardName, bundle: nil)
    return storyboard.instantiateInitialViewController() as? Self
  }
  @IBOutlet weak var nameLabel: UILabel!

  override func viewDidLoad() {
    super.viewDidLoad()
  }

  @IBAction func bookingTapped(_ sender: UIButton) {
    fatalError("Not implemented yet")
  }

  @IBAction func settingsTapped(_ sender: UIButton) {
    fatalError("Not implemented yet")
  }
  
  @IBAction func exitTapped(_ sender: UIButton) {
    fatalError("Not implemented yet")
  }
}

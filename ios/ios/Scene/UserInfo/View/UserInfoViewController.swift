//
//  UserInfoViewController.swift
//  ios
//
//  Created by Артем Розанов on 28.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit
import shared

protocol UserInfoView: UIViewController {}

final class UserInfoViewController: UIViewController, UserInfoView {
  static func storyboardInstance() -> Self? {
    let storyboard = UIStoryboard(name: String(describing: self), bundle: nil)
    return storyboard.instantiateInitialViewController() as? Self
  }

  private var profile: Profile?

  @IBOutlet weak var nameLabel: UILabel!

  override func viewDidLoad() {
    super.viewDidLoad()
    guard let profile = profile else { return }
    nameLabel.text = "\(profile.firstName) \(profile.lastName)"
    configureNavBar()
  }

  private func configureNavBar() {
    navigationItem.largeTitleDisplayMode = .never
    navigationController?.navigationBar.prefersLargeTitles = true
    navigationItem.title = "MyRest"
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

extension UserInfoViewController: ProfileDataDelegate {
  func passedProfile(_ profile: Profile) {
    self.profile = profile
  }
}

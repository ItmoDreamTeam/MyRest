//
//  RestaurantInfoViewController.swift
//  ios
//
//  Created by Артем Розанов on 14.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit

protocol RestaurantInfoView: UIViewController {}

final class RestaurantInfoViewController: UIViewController, RestaurantInfoView {

  private let photosCollectionViewHeight: CGFloat = 70

  private var reviews: [UserReviewViewModel] = []

  private var photosCollectionView: PhotosCollectionView
  private var tableView: UITableView

  init() {
    photosCollectionView = PhotosCollectionView()
    tableView = UITableView()
    super.init(nibName: nil, bundle: nil)
  }

  required init?(coder: NSCoder) {
    fatalError("init(coder:) has not been implemented")
  }

  override func viewDidLoad() {
    super.viewDidLoad()
    view.backgroundColor = .white
    configureNavBar()
    configureCollectionView()
    configureInfoView()
    configureTableView()
  }

  private func configureNavBar() {
    navigationItem.title = "MyRest"
    navigationItem.largeTitleDisplayMode = .always
    navigationController?.navigationBar.prefersLargeTitles = true
  }

  private func configureCollectionView() {
    view.addSubview(photosCollectionView)
    photosCollectionView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor).isActive = true
    photosCollectionView.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
    photosCollectionView.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
    photosCollectionView.heightAnchor.constraint(equalToConstant: photosCollectionViewHeight).isActive = true
  }

  private func configureInfoView() {
    fatalError("Not implemented yet")
  }

  private func configureTableView() {
    view.addSubview(tableView)
    tableView.topAnchor.constraint(equalTo: photosCollectionView.bottomAnchor).isActive = true
    tableView.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
    tableView.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
    tableView.bottomAnchor.constraint(equalTo: view.bottomAnchor).isActive = true
    tableView.dataSource = self
    tableView.delegate = self
    tableView.separatorStyle = .none
    tableView.showsVerticalScrollIndicator = false
    tableView.register(UINib(nibName: UserCell.reuseId, bundle: nil), forCellReuseIdentifier: UserCell.reuseId)
  }
}

extension RestaurantInfoViewController: UITableViewDataSource {
  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    reviews.count
  }

  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    guard
      let cell = tableView.dequeueReusableCell(withIdentifier: UserCell.reuseId, for: indexPath) as? UserCell
      else {
        return UITableViewCell()
    }
    cell.configure(with: reviews[indexPath.row])
    return cell
  }
}

extension RestaurantInfoViewController: UITableViewDelegate {
  func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
    fatalError("Not implemented yet")
  }
}

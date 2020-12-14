//
//  PhotosCollectionView.swift
//  ios
//
//  Created by Артем Розанов on 14.12.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import UIKit

final class PhotosCollectionView: UICollectionView, ConfigurableView {
  typealias Model = [RestaurantInfoViewModel]

  private let minimumLineSpacing: CGFloat = 3
  private let sectionInsets = UIEdgeInsets(top: 10, left: 10, bottom: 10, right: 10)
  private let cellWidth: CGFloat = 50

  private var viewModel: [RestaurantInfoViewModel] = []

  init() {
    let layout = UICollectionViewFlowLayout()
    layout.scrollDirection = .horizontal
    super.init(frame: .zero, collectionViewLayout: layout)
    backgroundColor = .white
    showsVerticalScrollIndicator = false
    showsHorizontalScrollIndicator = false
    delegate = self
    dataSource = self
    register(PhotoCell.self, forCellWithReuseIdentifier: PhotoCell.reuseId)
    translatesAutoresizingMaskIntoConstraints = false
    layout.minimumLineSpacing = minimumLineSpacing
    layout.sectionInset = sectionInsets
  }

  required init?(coder: NSCoder) {
    return nil
  }

  func configure(with model: [RestaurantInfoViewModel]) {
    self.viewModel = model
  }
}

extension PhotosCollectionView: UICollectionViewDataSource {
  func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
    return viewModel.count
  }

  func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
    guard
      let cell = dequeueReusableCell(
        withReuseIdentifier: PhotoCell.reuseId,
        for: indexPath
        ) as? PhotoCell
    else { return UICollectionViewCell() }
    cell.configure(with: viewModel[indexPath.row])
    return cell
  }
}

extension PhotosCollectionView: UICollectionViewDelegateFlowLayout {
  func collectionView(
    _ collectionView: UICollectionView,
    layout collectionViewLayout: UICollectionViewLayout,
    sizeForItemAt indexPath: IndexPath
  ) -> CGSize {
    return CGSize(width: cellWidth, height: frame.height * 0.8)
  }
}

extension PhotosCollectionView: UICollectionViewDelegate {}

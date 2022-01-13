module.exports = (sequelize, DataTypes) => {
  const brand = sequelize.define("brand", {
    brandId: {
      type: DataTypes.STRING,
      primaryKey: true,
    },
    brandName: {
      type: DataTypes.STRING,
    },
    brandImg: {
      type: DataTypes.STRING,
    },
  });

  brand.associate = (models) => {
    brand.hasMany(models.product, {
      foreignKey: "brandId",
      as: "product",
    });
  };

  return brand;
};

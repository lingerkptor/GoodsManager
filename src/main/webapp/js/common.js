const common = {
    sorting: function (unsortarray, compare) {
        if (unsortarray.length <= 1)
            return unsortarray;
        let less = [], pivotList = [], greater = [];
        let pivot = unsortarray[unsortarray.length - 1];
        pivotList.push(pivot);
        for (i = 0; i < (unsortarray.length - 1); i++) {
            let goods = unsortarray[i];
            let compareResult = compare(goods, pivot);
            if (compareResult < 0)
                less.push(goods);
            if (compareResult = 0)
                pivot.push(goods)
            if (compareResult > 0)
                greater.push(goods);
        }
        return sorting(less, compare).concat(pivotList).concat(sorting(greater, compare));
    },
};
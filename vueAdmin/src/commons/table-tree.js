export default {
  //展开或者关闭节点
  operateNode(rawTableList, tableList, openIds){
    for (let i = 0; i < rawTableList.length; i++) {
      tableList.push(rawTableList[i]);
      this.iterationResource(tableList, openIds, rawTableList[i]);
    }
  },
  //展开或者关闭节点
  operateNode(rawTableList, tableList, openIds, id){
    if (this.hasElement(openIds, id)) {
      for (let i = 0; i < openIds.length; i++) {
        if (openIds[i] === id) {
          openIds.splice(i, 1);
        }
      }
    } else {
      openIds.push(id);
    }
    for (let i = 0; i < rawTableList.length; i++) {
      tableList.push(rawTableList[i]);
      this.iterationResource(tableList, openIds, rawTableList[i]);
    }
  },
  //遍历添加展开的叶子
  iterationResource(tableList, openIds, res){
    //如果是被展开的
    if (this.hasElement(openIds, res.id)) {
      for (let i = 0; i < res.children.length; i++) {
        tableList.push(res.children[i]);
        if (this.hasElement(openIds, res.children[i].id)) {
          this.iterationResource(tableList, openIds, res.children[i]);
        }
      }
    }
  },
  //判断是否包含该元素
  hasElement(openIds, id){
    //判断是否包含元素
    for (let i = 0; i < openIds.length; i++) {
      if (id === openIds[i]) {
        return true;
      }
    }
    return false;
  },
}

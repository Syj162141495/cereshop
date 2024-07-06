/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.service.shop.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.shop.CerePlatformShopDAO;
import com.shop.cereshop.admin.page.index.Index;
import com.shop.cereshop.admin.page.index.MoneyTotal;
import com.shop.cereshop.admin.page.index.OrderInfo;
import com.shop.cereshop.admin.page.index.PersonTotal;
import com.shop.cereshop.admin.page.shop.ShopGetAll;
import com.shop.cereshop.admin.service.product.CereShopProductService;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.admin.page.finance.Finance;
import com.shop.cereshop.admin.page.finance.FinanceCount;
import com.shop.cereshop.admin.page.shop.Shop;
import com.shop.cereshop.admin.param.finance.FinanceParam;
import com.shop.cereshop.admin.param.shop.ShopGetAllParam;
import com.shop.cereshop.admin.param.shop.ShopSaveParam;
import com.shop.cereshop.admin.param.shop.ShopStartParam;
import com.shop.cereshop.admin.param.shop.ShopUpdateParam;
import com.shop.cereshop.admin.service.business.CereBusinessShopService;
import com.shop.cereshop.admin.service.business.CerePlatformBusinessService;
import com.shop.cereshop.admin.service.common.CommonService;
import com.shop.cereshop.admin.service.log.CerePlatformLogService;
import com.shop.cereshop.admin.service.shop.CerePlatformShopService;
import com.shop.cereshop.commons.constant.IntegerEnum;
import com.shop.cereshop.commons.domain.business.CereBusinessShop;
import com.shop.cereshop.commons.domain.business.CerePlatformBusiness;
import com.shop.cereshop.commons.domain.shop.CerePlatformShop;
import com.shop.cereshop.commons.domain.shop.CereShopCheck;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.domain.product.*;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.EncryptUtil;
import com.shop.cereshop.commons.utils.RandomStringUtil;
import com.shop.cereshop.commons.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Printable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CerePlatformShopServiceImpl implements CerePlatformShopService {

    @Autowired
    private CerePlatformShopDAO cerePlatformShopDAO;

    @Autowired
    private CerePlatformBusinessService cerePlatformBusinessService;

    @Autowired
    private CereBusinessShopService cereBusinessShopService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CerePlatformLogService cerePlatformLogService;

    @Autowired
    CereShopProductService cereShopProductService;

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void save(ShopSaveParam param, CerePlatformUser user) throws CoBusinessException {
        //新建商家数据
        String time= TimeUtils.yyMMddHHmmss();
        CerePlatformShop cerePlatformShop=new CerePlatformShop();
        cerePlatformShop.setShopName(param.getShopName());
        cerePlatformShop.setChargePersonName(param.getChargePersonName());
        cerePlatformShop.setChargePersonPhone(param.getChargePersonPhone());
        cerePlatformShop.setAddress(param.getAddress());
        cerePlatformShop.setCity(param.getCity());
        cerePlatformShop.setArea(param.getArea());
        cerePlatformShop.setClassifyId(param.getClassifyId());
        cerePlatformShop.setProvidersMajor(param.getProvidersMajor());
        cerePlatformShop.setProvidersSubclass(param.getProvidersSubclass());
        cerePlatformShop.setMedicalcollaboration(param.getMedicalcollaboration());
        cerePlatformShop.setServiceClassify(param.getServiceClassify());
        cerePlatformShop.setCoordinateX(param.getCoordinateX());
        cerePlatformShop.setCoordinateY(param.getCoordinateY());
        cerePlatformShop.setReditCode(param.getReditCode());
        cerePlatformShop.setInstitutionalClassify(param.getInstitutionalClassify());
        cerePlatformShop.setInstitutionalGrade(param.getInstitutionalGrade());
        cerePlatformShop.setIntroduction(param.getIntroduction());
        cerePlatformShop.setShopAdress(param.getShopAdress());
        cerePlatformShop.setEffectiveDate(param.getEffectiveDate());
        cerePlatformShop.setEffectiveYear(param.getEffectiveYear());
        cerePlatformShop.setContractState(param.getContractState());
        cerePlatformShop.setShopPhone(param.getShopPhone());
        if(EmptyUtils.isEmpty(param.getShopPassword())){
            cerePlatformShop.setShopPassword(EncryptUtil.encrypt("123456"));
        }else {
            cerePlatformShop.setShopPassword(EncryptUtil.encrypt(param.getShopPassword()));
        }
        //生成店铺编号
        String shopCode=commonService.getShopCode();
        cerePlatformShop.setShopCode(shopCode);
        cerePlatformShop.setAuthenticationState(IntegerEnum.APPROVED.getCode());
        cerePlatformShop.setCheckState(IntegerEnum.UNTREATED.getCode());
        cerePlatformShop.setState(IntegerEnum.YES.getCode());
        cerePlatformShop.setCreateTime(time);
        cerePlatformShopDAO.insert(cerePlatformShop);
        //同步新增商家用户数据
        CerePlatformBusiness cerePlatformBusiness=new CerePlatformBusiness();
        cerePlatformBusiness.setCreateTime(time);
        cerePlatformBusiness.setUsername(cerePlatformShop.getShopPhone());
        cerePlatformBusiness.setPassword(cerePlatformShop.getShopPassword());
        cerePlatformBusiness.setName(cerePlatformShop.getChargePersonName());
        cerePlatformBusiness.setState(IntegerEnum.YES.getCode());
        cerePlatformBusiness.setToken(RandomStringUtil.getRandomCode(128,0));
        cerePlatformBusinessService.insert(cerePlatformBusiness);
        //同步新增商家用户绑定店铺数据
        CereBusinessShop cereBusinessShop=new CereBusinessShop();
        cereBusinessShop.setBusinessUserId(cerePlatformBusiness.getBusinessUserId());
        cereBusinessShop.setShopId(cerePlatformShop.getShopId());
        cereBusinessShop.setIfBinding(IntegerEnum.YES.getCode());
        cereBusinessShopService.insert(cereBusinessShop);
        //新增日志
        cerePlatformLogService.addLog(user,"商家管理","平台端操作","添加商家",String.valueOf(cereBusinessShop.getShopId()),time);
    }

    @Override
    public CerePlatformShop findByShopName(String shopName) {
        return cerePlatformShopDAO.findByShopName(shopName);
    }

    @Override
    public CerePlatformShop findByPhone(String shopPhone) {
        return cerePlatformShopDAO.findByPhone(shopPhone);
    }

    @Override
    public CerePlatformShop checkShopIdByName(String shopName, Long shopId) {
        return cerePlatformShopDAO.checkShopIdByName(shopName,shopId);
    }

    @Override
    public CerePlatformShop checkShopIdByPhone(String shopPhone, Long shopId) {
        return cerePlatformShopDAO.checkShopIdByPhone(shopPhone,shopId);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void update(ShopUpdateParam param, CerePlatformUser user) throws CoBusinessException {
        String time= TimeUtils.yyMMddHHmmss();
        CerePlatformShop cerePlatformShop=new CerePlatformShop();
        cerePlatformShop.setUpdateTime(time);
        cerePlatformShop.setShopId(param.getShopId());
        cerePlatformShop.setShopName(param.getShopName());
        cerePlatformShop.setChargePersonName(param.getChargePersonName());
        cerePlatformShop.setChargePersonPhone(param.getChargePersonPhone());
        cerePlatformShop.setShopAdress(param.getShopAdress());
        cerePlatformShop.setAddress(param.getAddress());
        cerePlatformShop.setCity(param.getCity());
        cerePlatformShop.setArea(param.getArea());
        cerePlatformShop.setServiceClassify(param.getServiceClassify());
        cerePlatformShop.setProvidersMajor(param.getProvidersMajor());
        cerePlatformShop.setProvidersSubclass(param.getProvidersSubclass());
        cerePlatformShop.setClassifyId(param.getClassifyId());
        cerePlatformShop.setMedicalcollaboration(param.getMedicalcollaboration());
        cerePlatformShop.setCoordinateX(param.getCoordinateX());
        cerePlatformShop.setCoordinateY(param.getCoordinateY());
        cerePlatformShop.setReditCode(param.getReditCode());
        cerePlatformShop.setInstitutionalClassify(param.getInstitutionalClassify());
        cerePlatformShop.setInstitutionalGrade(param.getInstitutionalGrade());
        cerePlatformShop.setIntroduction(param.getIntroduction());
        cerePlatformShop.setEffectiveDate(param.getEffectiveDate());
        cerePlatformShop.setEffectiveYear(param.getEffectiveYear());
        cerePlatformShop.setContractState(param.getContractState());
        cerePlatformShop.setShopPhone(param.getShopPhone());
        cerePlatformShop.setShopPassword(EncryptUtil.encrypt(param.getShopPassword()));
        //修改商家数据
        cerePlatformShopDAO.updateByPrimaryKeySelective(cerePlatformShop);
        //根据商家用户id查询用户数据
        CerePlatformBusiness cerePlatformBusiness=cerePlatformShopDAO.findBusiness(param.getBusinessUserId());
        if(cerePlatformBusiness!=null){
            cerePlatformBusiness.setUpdateTime(time);
            cerePlatformBusiness.setUsername(cerePlatformShop.getShopPhone());
            cerePlatformBusiness.setPassword(cerePlatformShop.getShopPassword());
            cerePlatformBusiness.setName(cerePlatformShop.getChargePersonName());
            cerePlatformBusinessService.update(cerePlatformBusiness);
        }
        //新增日志
        cerePlatformLogService.addLog(user,"商家管理","平台端操作","修改商家",String.valueOf(cerePlatformShop.getShopId()),time);
    }

    @Override
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = {CoBusinessException.class, Exception.class})
    public void startOrStop(ShopStartParam param, CerePlatformUser user) throws CoBusinessException {
        String time =TimeUtils.yyMMddHHmmss();
        CerePlatformShop cerePlatformShop=new CerePlatformShop();
        cerePlatformShop.setShopId(param.getShopId());
        cerePlatformShop.setState(param.getState());
        String describe="";
        if(IntegerEnum.YES.getCode().equals(cerePlatformShop.getState())){
            describe="启用商家";
        }else {
            describe="停用商家";
        }
        cerePlatformShop.setUpdateTime(time);
        cerePlatformShopDAO.updateByPrimaryKeySelective(cerePlatformShop);
        //同时下架所有商品
        cereShopProductService.unShelveByShopId(param.getShopId(), user);
        //新增日志
        cerePlatformLogService.addLog(user,"商家管理","平台端操作",describe,String.valueOf(cerePlatformShop.getShopId()),time);
    }

    @Override
    public ShopGetAll getById(Long shopId) throws CoBusinessException,Exception {
        ShopGetAll shop = cerePlatformShopDAO.getById(shopId);
        //解密密码
        shop.setShopPassword(EncryptUtil.decrypt(shop.getShopPassword()));
        return shop;
    }

    @Override
    public Page getAll(ShopGetAllParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<ShopGetAll> list=cerePlatformShopDAO.getAll(param);
        PageInfo<ShopGetAll> pageInfo=new PageInfo<>(list);
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        return page;
    }

    @Override
    public Shop findShop(Long shopId) {
        return cerePlatformShopDAO.findShop(shopId);
    }

    @Override
    public void updateDate(CerePlatformShop cerePlatformShop) throws CoBusinessException {
        cerePlatformShopDAO.updateDate(cerePlatformShop);
    }

    @Override
    public FinanceCount getAllFinance(FinanceParam param) throws CoBusinessException {
        PageHelper.startPage(param.getPage(),param.getPageSize());
        List<Finance> list=cerePlatformShopDAO.getAllFinance(param);
        PageInfo<Finance> pageInfo=new PageInfo<>(list);
        if(!EmptyUtils.isEmpty(list)){
            list=list.stream()
                    //查询冻结金额
                    .peek(a -> a.setFrozen(cerePlatformShopDAO.finFrozen(a.getShopId())))
                    //查询已提金额
                    .peek(a -> a.setHaveWithdrawable(cerePlatformShopDAO.findHaveWithdrawal(a.getShopId())))
                    //查询已退款金额
                    .peek(a -> a.setRefund(cerePlatformShopDAO.findRefund(a.getShopId())))
                    .collect(Collectors.toList());
            pageInfo.setList(list);
        }
        Page page=new Page(pageInfo.getList(),pageInfo.getTotal());
        FinanceCount count=new FinanceCount();
        BigDecimal decimal=new BigDecimal(0);
        //查询营收总额
        count.setRevenue(cerePlatformShopDAO.findRevenue());
        //查询冻结中金额
        count.setFrozen(cerePlatformShopDAO.finFrozen(null));
        //查询总的提现金额
        BigDecimal withdrawableMoney = cerePlatformShopDAO.getAllWithdrawableMoney();
        //查询提现中的金额
        BigDecimal stayMoney = cerePlatformShopDAO.getWithdrawableStayMoney();
        //查询已提现金额
        BigDecimal have=cerePlatformShopDAO.findHaveWithdrawal(null);
        count.setHaveWithdrawable(have);
        //计算可提现金额=总的提现金额-提现中金额-已提现金额
        count.setWithdrawable(decimal.add(withdrawableMoney).subtract(stayMoney).subtract(have));
        //查询已退款金额
        count.setRefund(cerePlatformShopDAO.findRefund(null));
        count.setPage(page);
        return count;
    }

    @Override
    public Long findBusinessId(Long shopId) {
        return cerePlatformShopDAO.findBusinessId(shopId);
    }

    @Override
    public List<CerePlatformShop> findAll() {
        return cerePlatformShopDAO.findAll();
    }

    @Override
    public void updateShopStop(CerePlatformShop shop) throws CoBusinessException {
        cerePlatformShopDAO.updateShopStop(shop);
    }

    @Override
    public Index getIndex() throws CoBusinessException {
        Index index = new Index();
        Integer shopNum = cerePlatformShopDAO.getShopNum();
        index.setShopNum(shopNum);
        index.setNewShopNum(cerePlatformShopDAO.getNewShop());
        index.setUnCheckShopNum(cerePlatformShopDAO.getUnCheckShop());
        Integer personNum = cerePlatformShopDAO.getPersonNum();
        index.setPersonNum(personNum);
        index.setActivityPersonNum(cerePlatformShopDAO.getActivityPerson());
        Integer classifyNum = cerePlatformShopDAO.getClassifyNum();
        index.setClassifyNum(classifyNum);
        Integer productNum = cerePlatformShopDAO.getProductNum();
        index.setProductNum(productNum);
        index.setPublishProductNum(cerePlatformShopDAO.getPublishProduct());
        index.setUnPublishProductNum(cerePlatformShopDAO.getUncheckProduct());
        Integer orderNum = cerePlatformShopDAO.getOrderNum();
        index.setOrderNum(orderNum);
        index.setNewOrderNum(cerePlatformShopDAO.getNewOrderNum());
        List<OrderInfo> list = cerePlatformShopDAO.getNewOrderList();
        index.setOrderList(list);
        List<String> monthList = getMonthList(6);
        List<String> moneyList = new ArrayList<>();
        List<String> personList = new ArrayList<>();
        MoneyTotal moneyTotal = new MoneyTotal();
        moneyTotal.setMonth(monthList);
        PersonTotal personTotal = new PersonTotal();
        personTotal.setMonthList(monthList);
        BigDecimal bigDecimal1 = new BigDecimal("0");
        BigDecimal bigDecimal2 = new BigDecimal("0");
        for(int i = 0 ; i < monthList.size(); i++){
            String month = monthList.get(i);
            String personSum = cerePlatformShopDAO.getPerson(month);
            String moneySum = cerePlatformShopDAO.getMoney(month);
            personList.add(personSum);
            moneyList.add(moneySum);
            moneyTotal.setData(moneyList);
            personTotal.setPersonList(personList);
            bigDecimal1 = bigDecimal1.add(new BigDecimal(personSum));
            bigDecimal2 = bigDecimal2.add(new BigDecimal(moneySum));
        }
        Integer newPersonNum = bigDecimal1.intValue();
        index.setMoneyNum(bigDecimal2);
        index.setNewPersonNum(newPersonNum);
        index.setMoneyTotal(moneyTotal);
        index.setPersonTotal(personTotal);
        Integer newMonthPersonNum = cerePlatformShopDAO.getCurrentMonthPerson();
        index.setNewMonthPersonNum(newMonthPersonNum);
        return index;
    }

    // 获取最近N个月的月份数组
    public List<String> getMonthList(Integer n){
        List<String> monthList = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for(int i = 0; i < n; i++){
            monthList.add(now.minusMonths(i).format(DateTimeFormatter.ofPattern("yyyy-MM")));
        }
        Collections.reverse(monthList);
        return monthList;
    }


}

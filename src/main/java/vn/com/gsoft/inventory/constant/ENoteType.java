package vn.com.gsoft.inventory.constant;

public class ENoteType {
    /// <summary>
    /// Receipt  - Nhập kho
    /// </summary>
    public static final Long Receipt = 1L;

    /// <summary>
    /// Delivery
    /// </summary>
    public static final Integer Delivery = 2;
    public static final String DeliveryStr = "2";

    /// <summary>
    /// Return from customer  - Nhập kho Khách hàng trả lại
    /// </summary>
    public static final Integer ReturnFromCustomer = 3;

    /// <summary>
    /// Return to supplier - Xuất Trả lại nhà cung cấp
    /// </summary>
    public static final Integer ReturnToSupplier = 4;

    /// <summary>
    /// Inventory adjustment - Nhật + Xuất Điều chỉnh hàng tồn kho
    /// </summary>
    public static final Integer InventoryAdjustment = 5;

    /// <summary>
    /// Initial inventory   - Hàng tồn kho ban đầu
    /// </summary>
    public static final Integer InitialInventory = 6;

    /// <summary>
    /// Initial Supplyer Debt (nợ đầu kỳ của nhà cung cấp trước khi khách hàng dùng Web Nhà thuốc)
    /// </summary>
    public static final Integer InitialSupplierDebt = 7;

    /// <summary>
    /// Warehouse transfer
    /// </summary>
    public static final Integer WarehouseTransfer = 8;

    /// <summary>
    /// Cancel delivery - Xuất hủy
    /// </summary>
    public static final Integer CancelDelivery = 9;

    /// <summary>
    /// Quality control book
    /// </summary>
    public static final Integer QualityControlBook = 10;
    //Phiếu khám bệnh
    public static final Integer ExaminationCard = 11;
    //Phiếu dịch vụ
    public static final Integer NoteService = 12;
    //Phiếu chờ khám
    public static final Integer NoteWait = 13;
    //Phiếu thu tiền
    public static final Integer ReceiptMedicalFee = 14;
    //Phiếu dự trù
    public static final Integer NoteReserve = 15;
    //Phiếu đơn mẫu
    public static final Integer SampleForm = 16;
    //Phiếu kiểu kê
    public static final Integer InventoryForm = 17;
    //Mã vạch
    public static final Integer MedicineBarcodeSample = 18;
    //Lịch sử dao dịch
    public static final Integer ransactionHistory  = 19;
}
